#!/usr/bin/env groovy
import hudson.model.*
import hudson.EnvVars
import java.net.URL

node {
	stage('Git Checkout'){
	git 'https://github.com/obyerere/TeaCup-BE.git'
	}
	
	stage('compile'){
    withMaven(maven: 'maven') {
    sh 'mvn compile' // using pipeline syntax
		}	
	}
	
	stage('code review'){
	try{
    withMaven(maven: 'maven') {
    sh 'mvn pmd:pmd' 
			}
		}finally {
		pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/pmd.xml', unHealthy: ''
		}
	}

	stage('Test'){
	try{
    withMaven(maven: 'maven') {
    sh 'mvn test' 
			}
		}finally {
		junit 'target/surefire-reports/TEST-com.grokonez.jwtauthentication.TestBootUp.xml'
		}
	}
	
	stage('code coverage'){
	try{
    withMaven(maven: 'maven') {
    sh 'mvn cobertura:cobertura -Dcobertura.report.format=xml' 
			}
		}finally{
		cobertura autoUpdateHealth: false, autoUpdateStability: false, coberturaReportFile: 'target/site/cobertura/coverage.xml', conditionalCoverageTargets: '70, 0, 0', failUnhealthy: false, failUnstable: false, lineCoverageTargets: '80, 0, 0', maxNumberOfBuilds: 0, methodCoverageTargets: '80, 0, 0', onlyStable: false, sourceEncoding: 'ASCII', zoomCoverageChart: false
		}	
	}
	
	stage('package'){
	try{
    withMaven(maven: 'maven') {
    sh 'mvn package' 
			}
		}finally{
		archiveArtifacts 'target/*.jar'
		}
	}
	
}
