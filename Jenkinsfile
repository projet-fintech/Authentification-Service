pipeline {
    agent any
    tools {
        maven 'Maven'
         jdk 'JAVA21'
    }
    stages {
       stage('Checkout') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[credentialsId: 'ser3elah', url: 'https://github.com/projet-fintech/Authentification-Service.git']]
                )
            }
        }
        stage('Building') {
            steps {
                dir('Authentication_service') { // Assure-toi que c'est le bon répertoire
                    sh 'mvn clean install -DskipTests=true'
                }
            }
            }
        }
    }
