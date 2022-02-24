
pipeline {
    agent any

    tools {
        terraform 'Terraform14'
        maven('maven3.0.5')
        jdk('jdk11')

    }
    stages{
       stage('Git Checkout') {
           steps {
                git branch: 'main', credentialsId: 'gkfka133', url: 'https://github.com/PARKHARAM/spring' 
         }      
        }
        

        
        stage('SonarQube analysis') {
            steps{
                withSonarQubeEnv('testsonar'){
                    sh "pwd"
                    dir("mvntest"){
                        sh "pwd"
                        sh "ls -al"
                        sh "mvn clean package"
                        sh "mvn sonar:sonar -Dsonar.projectKey=demo -Dsonar.host.url=http://34.64.88.47:9000/ -Dsonar.login=9f40ea6d870c2c7b24f4ecc6f40350b8030a170a"
     
                    }
                 }
            }
        }
        
        stage('SonarQube Quality Gate'){
            steps{
                timeout(time: 1, unit: 'MINUTES') {
                    script{
                        echo "Start~~~~"
                        def qg = waitForQualityGate()
                        echo "Status: ${qg.status}"
                        if(qg.status != 'OK') {
                            echo "NOT OK Status: ${qg.status}"
                            //updateGitlabCommitStatus(name: "SonarQube Quality Gate", state: "failed")
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        } else{
                            echo "OK Status: ${qg.status}"
                            //updateGitlabCommitStatus(name: "SonarQube Quality Gate", state: "success")
                        }
                        echo "End~~~~"
                    }
                }
            }
        }
    }
}
