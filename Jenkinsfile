pipeline {
    agent any
    stages {
        stage('Cleaning Workspace') {
            steps {
                cleanWs()
            }
        }
        stage('Git Checkout'){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/13v13reddy/simple-maven-project-with-tests.git']])
            }
        }
        stage('Maven Build'){
            steps{
                sh '''
                    mvn clean
                    mvn package -DskipTests
                    '''
            }
        }
        stage('Archive Artifacts'){
            steps{
                archiveArtifacts artifacts: 'target/*.jar', followSymlinks: false
            }
        }
        stage('Upload Artifacts') {
    steps {
        nexusArtifactUploader(
            nexusVersion: 'nexus2',
            protocol: 'http',
            nexusUrl: '98.82.0.245:8081/nexus',
            groupId: 'test',
            version: '1.0',
            repository: 'devops',
            credentialsId: 'nexusCred',
            artifacts: [
                [artifactId: 'simple-maven-project-with-tests',
                 type: 'jar',
                 file: 'target/simple-maven-project-with-tests-1.0.jar']
            ]
        )
    }}
    stage('Docker Build and Push'){
        steps{
            sh '''
                  aws ecr get-login-password --region us-east-1 | \
                    docker login --username AWS --password-stdin 148761656198.dkr.ecr.us-east-1.amazonaws.com
                  docker build -t 148761656198.dkr.ecr.us-east-1.amazonaws.com/13v13reddy/test:${BUILD_NUMBER} .
                  docker push 148761656198.dkr.ecr.us-east-1.amazonaws.com/13v13reddy/test:${BUILD_NUMBER}
               '''
        }
    }
}
}