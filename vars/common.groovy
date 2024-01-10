def compile() {
    if (app_lang == "nodejs" ) {
        sh 'npm install'
    }

    if (app_lang == "maven" ) {
        sh 'mvn package && cp target/${component}-1.0.jar ${component}.jar'
    }
}

def unittests() {
    if (app_lang == "nodejs" ) {
        sh 'npm test || true'
    }

    if (app_lang == "maven" ) {
        sh 'mvn test'
    }

    if (app_lang == "python" ) {
        sh 'python3 -m unittest'
    }
}

def email(email_note) {
    mail bcc: '', body: "Job failed - ${JOB_BASE_NAME}\nJenkins URL - ${JOB_URL} ", cc: '', from: 'paparao.prema75@gmail.com', replyTo: '', subject: 'test from cart', to: 'paparao.prema75@gmail.com'
}

def artifact_Push() {
    sh "echo ${TAG_NAME} >VERSION"

    if (app_lang == "nodejs") {
       sh "zip -r  ${component}-${TAG_NAME}.zip nodule_modules package.json server.js VERSION ${extrafiles}"
    }

    if (app_lang == "nginx" || app_lang == "python") {
        sh "zip -r  ${component}-${TAG_NAME}.zip * -x Jenkinsfile ${extrafiles}"
    }

    if (app_lang == "maven") {
        sh "zip -r  ${component}-${TAG_NAME}.zip ${component}.jar VERSION ${extrafiles}"
    }

    sh "curl -v -u admin:admin123 --upload-file ${component}-${TAG_NAME}.zip http://172.31.88.150:8081/repository/${component}/${component}-${TAG_NAME}.zip"
}