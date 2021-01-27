pipeline {
    agent {
        label 'docker-compose'
    }
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven 'Maven3'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'selenium-grid-configuration',
                    url: 'https://github.com/vunicjovan/qa-learning.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests > build-log-file.log'
            }
        }
        stage('Test') {
            parallel {
                stage('Run Docker file') {
                    steps {
                        bat 'docker-compose up --scale chrome=5'
                    }
                }
                stage('Build') {
                    steps {
                        bat 'mvn clean test > test-log-file.log'
                    }
                    post {
                        always {
                            archiveArtifacts artifacts: 'build-log-file.log'
                            archiveArtifacts artifacts: 'test-log-file.log'
                            archiveArtifacts artifacts: '**/screenshots/**'
                            junit 'target/surefire-reports/*.xml'
                            bat 'docker-compose down'
                        }
                    }
                }
            }
        }
    }
    post {
        success {
          echo 'Pipeline has finished successfully.'
        }
    }
}
