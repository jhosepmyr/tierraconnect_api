pipeline {
    agent any

    tools {
        maven 'Maven3.8.8'
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
                    pkill -f 'api-0.0.1-SNAPSHOT.jar' || true
                    sleep 2

                    # Ejecutar nuevo JAR
                    nohup java -jar target/api-0.0.1-SNAPSHOT.jar > /tmp/app.log 2>&1 &

                    # Esperar inicio
                    sleep 10

                    # Verificar
                    curl -f http://localhost:8080/api/productos/health || exit 1

                    echo "Aplicación desplegada exitosamente"
                '''
            }
        }
    }

    post {
        success {
            echo 'CI/CD Pipeline completado exitosamente'
        }
        failure {
            echo 'Pipeline falló'
        }
    }
}