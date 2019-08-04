# koref
A coreference resolution engine based on Reconcile written in Kotlin.

## Getting Started

* ./gradlew build
* ./gradlew run - Run KorefDriver
* ./gradlew clean test - Run Unit Tests
* ./gradlew detekt - Linting
* ./gradlew jacocoTestReports - Generate Code Coverage
* ./graldew jacocoTestCoverageVerification - Check if coverage meets specifications
* ./gradlew dokka - Generate Documentation

## Roadmap to 1.0

* Read in corpus, apply preprocessors 
    * Byte spans for all annotations
* Re-implement Reconcile Data Flow with key exceptions.
    * Client/Server Model
    * Modern and diverse Annotation Packages
    * Able to handle legacy and some modern data sets
    
