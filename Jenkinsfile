pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven 'Maven3'
    }
    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main',
                    url: 'https://github.com/vunicjovan/qa-learning.git'
            }
        }
        stage('Build & Test') {
            steps {
                // To run Maven on a Windows agent, use
                bat 'mvn clean install > log-file.log'
            }
        }
        stage('Archive artifacts and publish results') {
            steps {
                archiveArtifacts artifacts: 'log-file.log'
                archiveArtifacts artifacts: '**/screenshots/**'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
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
