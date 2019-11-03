pipeline {
    agent any
    stages {
        stage('CLEAN') {
            steps {
				sh 'mvn clean'
            }
        }
        stage('BUILD') {
            steps {
                sh 'mvn package -DskipTests=true'				
            }
        }
        stage('DOCKER-BUILD') {
            steps {
                sh 'docker build -t scraper/scraper .'
            }
        }
        stage('DOCKER-RUN') {
     	    steps {
        	    sh 'docker stop scraper || true'
            	sh 'docker rm scraper || true'
            	sh 'docker run --name="scraper" -d -p 80:8080 scraper/scraper'
            }
        }
    }
	post {
	    always {
	        archiveArtifacts artifacts: '**/*.war',
		    onlyIfSuccessful: true
	    }
	}	
}