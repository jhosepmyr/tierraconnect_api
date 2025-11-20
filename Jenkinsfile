pipeline {
    agent any

    tools {
        maven 'Maven3.8.8'
    }

    environment {
        APP_NAME = 'tierraconnect-api'
        JAR_NAME = 'api-0.0.1-SNAPSHOT.jar'
        APP_PORT = '8080'
        LOG_FILE = '/var/log/tierraconnect-api.log'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Obteniendo código desde GitHub...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compilando aplicación con Maven...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Ejecutando tests unitarios...'
                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Archive') {
            steps {
                echo 'Archivando artefactos...'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

        stage('Stop Previous Version') {
            steps {
                echo 'Deteniendo versión anterior...'
                script {
                    sh '''
                        #!/bin/bash
                        PID=$(pgrep -f 'api-0.0.1-SNAPSHOT.jar')
                        if [ ! -z "$PID" ]; then
                            echo "Deteniendo proceso anterior (PID: $PID)..."
                            kill -9 $PID || true
                            sleep 3
                            echo "Aplicación anterior detenida"
                        else
                            echo "No hay aplicación anterior corriendo"
                        fi
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Desplegando nueva versión...'
                script {
                    sh '''
                        #!/bin/bash

                        # Crear directorio de logs
                        mkdir -p /var/log
                        touch ${LOG_FILE}

                        # Ejecutar el JAR en background
                        cd ${WORKSPACE}/target
                        nohup java -jar ${JAR_NAME} > ${LOG_FILE} 2>&1 &

                        # Guardar PID
                        echo $! > /tmp/${APP_NAME}.pid

                        echo "Esperando que la aplicación inicie..."
                        sleep 15
                    '''
                }
            }
        }

        stage('Health Check') {
            steps {
                echo 'Verificando que la aplicación esté corriendo...'
                script {
                    sh '''
                        #!/bin/bash

                        # Verificar proceso
                        if [ -f /tmp/${APP_NAME}.pid ]; then
                            PID=$(cat /tmp/${APP_NAME}.pid)
                            if ps -p $PID > /dev/null; then
                                echo "Proceso corriendo (PID: $PID)"
                            else
                                echo "El proceso no está corriendo"
                                exit 1
                            fi
                        fi

                        # Health check HTTP
                        MAX_RETRIES=5
                        RETRY_COUNT=0

                        while [ $RETRY_COUNT -lt $MAX_RETRIES ]; do
                            echo "Intento $((RETRY_COUNT + 1)) de $MAX_RETRIES..."

                            HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:${APP_PORT}/api/productos/health || echo "000")

                            if [ "$HTTP_CODE" = "200" ]; then
                                echo "Health check exitoso! (HTTP $HTTP_CODE)"

                                # Mostrar respuesta del health endpoint
                                RESPONSE=$(curl -s http://localhost:${APP_PORT}/api/productos/health)
                                echo "Respuesta: $RESPONSE"

                                echo "TierraConnect API desplegada correctamente!"
                                echo "Accesible en: http://localhost:${APP_PORT}/api/productos"
                                exit 0
                            fi

                            echo "Health check falló (HTTP $HTTP_CODE), reintentando..."
                            RETRY_COUNT=$((RETRY_COUNT + 1))
                            sleep 5
                        done

                        echo "Health check falló después de $MAX_RETRIES intentos"
                        echo "Últimas líneas del log:"
                        tail -20 ${LOG_FILE}
                        exit 1
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado exitosamente!'
        }
        failure {
            echo 'Pipeline falló!'
        }
        always {
            echo 'Proceso completado'
        }
    }
}