#!/usr/bin/env groovy
import hudson.model.*
import hudson.EnvVars
import java.net.URL 

node {
    stage('Git Checkout'){
        git 'https://github.com/jamunakan2307/demo-be.git'
    }
    
    stage('Compile Code'){
        withMaven(maven: 'Maven_3.6.3'){
            sh 'mvn compile'
        }
        
    }
    
      stage('Code Review'){
     try {
         withMaven(maven: 'Maven_3.6.3'){
            sh 'mvn pmd:pmd'
        }
        } finally {
			pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/pmd.xml', unHealthy: ''
    }
        
    }
    
    stage('Run Test'){
       
    try {
        withMaven(maven: 'Maven'){
            sh 'mvn test'
        } 
        } finally {
            junit 'target/surefire-reports/TEST-com.grokonez.jwtauthentication.TestBootUp.xml'
    }
    }
        stage('Code Coberage'){
            try {
        withMaven(maven: 'Maven'){
            sh 'mvn cobertura:cobertura -Dcobertura.report.format=xml'
        } 
        } finally {
            cobertura autoUpdateHealth: false, autoUpdateStability: false, coberturaReportFile: 'target/site/cobertura/coverage.xml', conditionalCoverageTargets: '70, 0, 0', failUnhealthy: false, failUnstable: false, lineCoverageTargets: '80, 0, 0', maxNumberOfBuilds: 0, methodCoverageTargets: '80, 0, 0', onlyStable: false, sourceEncoding: 'ASCII', zoomCoverageChart: false
    } 
            
        }


     stage('Prepare Package'){
         try {
        withMaven(maven: 'Maven'){
            sh 'mvn package'
        } 
        } finally {
            archiveArtifacts 'target/*.jar'
    }
    }
}
