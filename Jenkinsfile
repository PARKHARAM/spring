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
        stage('Sonarqube Analysis') {
            when {
                expression {
                    return env.gitcloneResult ==~ /(?i)(Y|YES|T|TRUE|ON|RUN)/
                }
            }
            steps {
                script {
                    try {
                        withSonarQubeEnv('testsonar') {
                            sh 'mvn clean package -DskipTests -Djib.container.environment=SPRING_PROFILES_ACTIVE=dev sonar:sonar'
                        }
                        env.sonarResult = true
                    }
                    catch(Exception e) {
                        print(e)
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }
        stage('Quality Gate') {
            when {
                expression {
                    return env.sonarResult ==~ /(?i)(Y|YES|T|TRUE|ON|RUN)/
                }
            }
            steps {
                script {
                    try {
                        timeout(time: 1, unit: 'HOURS') {
                            def qg = waitForQualityGate()
                            if (qg.status != 'OK') {
                                error "Pipeline aborted due to quality gate failure: ${qg.status}"
                            }
                        }
                        env.qualityGateResult = true
                    }
                    catch(Exception e) {
                        print(e)
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }        
        

    }
}

