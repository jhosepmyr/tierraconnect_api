pipeline {
    agent any

    tools {
        maven 'Maven3.8.8'
    }

    environment {
        APP_PORT = '8081'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Clonando código desde GitHub...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compilando con Maven...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Desplegando aplicación...'
                sh '''
                    # Matar proceso anterior si existe
                    pkill -f 'api-0.0.2-SNAPSHOT.jar' || true
                    sleep 2

                    # Ejecutar nuevo JAR
                    nohup java -jar target/api-0.0.2-SNAPSHOT.jar > /tmp/app.log 2>&1 &

                    # Esperar inicio
                    echo "Esperando 30 segundos para que la aplicación inicie..."
                    sleep 30

                    # Verificar con reintentos
                    for i in {1..10}; do
                        echo "Intento $i de 10..."
                        if curl -f http://localhost:8081/api/productos/health; then
                            echo "✅ Aplicación desplegada exitosamente en puerto 8081"
                            exit 0
                        fi
                        sleep 3
                    done

                    echo "La aplicación no responde"
                    tail -50 /tmp/app.log
                    exit 1
                '''
            }
        }
    }
}