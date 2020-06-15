pipeline {
 environment {
  registry = "obyerere1/tea-be"
  registryCredential = 'Dockerhub'
  dockerImage = ''
 }
 agent any
 stages {
  stage('Git Checkout') {
   steps {
    git 'https://github.com/obyerere/TeaCup-BE.git'
   }
  }

  stage('Prepare Package') {
   steps {
    withMaven(maven: 'maven') {
     sh 'mvn package'
    }
   }
  }

  stage('Building image') {
   steps{
    script {
     dockerImage = docker.build registry + ":v0.2"
    }
   }
  }
  stage('Deploy Image') {
   steps{
    script {
     docker.withRegistry( '', registryCredential ) {
      dockerImage.push()
     }
    }
   }
  }
  stage('Remove Unused docker image') {
   steps{
    sh "docker rmi $registry:v0.2"
   }
  }
 }
}

