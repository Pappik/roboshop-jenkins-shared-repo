def call() {

    if(!env.SONAR_EXTRA_OPTS){
        env.SONAR_EXTRA_OPTS = " "
    }
    if(!env.TAG_NAME) {
        env.PUSH_CODE = "false"
    }else {
        env.PUSH_CODE = "true"
    }

    try {
        node('workstation') {

            stage('CleanUp') {
                cleanWs()
                git branch: 'main', url: "https://github.com/Pappik/${component}"
            }
            stage('compile') {
                common.compile()
            }

            stage('unit test') {
                common.unittests()
            }

            stage('Quality Control') {
//                sh "sonar-scanner -Dsonar.host.url=http://172.31.86.114:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"
                sh "echo sonar scan"

            }

            if( env.PUSH_CODE == true ) {
                common.artifact_Push()
            }


        }
    }catch(Exception e) {
        common.email("failed")
    }
}

