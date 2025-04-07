pipeline {
  agent any

  environment {
    AWS_REGION    = 'us-west-1'
    CLUSTER_NAME  = 'Prod-EKS'   // Change per environment
    IMAGE_NAME    = '13v13reddy/testrepo'
    DOCKER_CREDS  = credentials('docker-hub-credentials') // Jenkins Credentials ID
    AWS_CREDS     = credentials('awsCred')            // Jenkins Credentials ID
  }

  stages {
    stage('Checkout Code') {
      steps {
        git branch: 'master', url: 'https://github.com/13v13reddy/simple-maven-project-with-tests.git'
      }
    }

    stage('Build Java App') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }

    stage('Docker Build & Push') {
      steps {
        sh '''
          echo $DOCKER_CREDS_PSW | docker login -u $DOCKER_CREDS_USR --password-stdin
          docker buildx build --platform linux/amd64 -t $IMAGE_NAME:$BUILD_NUMBER .
          docker push $IMAGE_NAME:$BUILD_NUMBER
        '''
      }
    }

    stage('Configure AWS CLI') {
      steps {
        withAWS(credentials: 'awsCred', region: 'us-west-1') {
        sh '''
        aws eks update-kubeconfig --region $AWS_REGION --name $CLUSTER_NAME
      '''}
      }
    }

    stage('Deploy to EKS') {
      steps {
        sh '''
          kubectl set image deployment/java-app testrepo=$IMAGE_NAME:$BUILD_NUMBER --namespace=default
        '''
      }
    }
  }
}
