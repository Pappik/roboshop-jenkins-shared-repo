def call() {
    if(!SONAR_EXTRA_OPTS){
        env.SONAR_EXTRA_OPTS = " "
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
                sh "sonar-scanner -Dsonar.host.url=http://172.31.86.114:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"

            }
            stage('Upload Artifact') {
                echo 'Upload artifacts'
            }
        }
    }catch(Exception e) {
        common.email("failed")
    }
}

