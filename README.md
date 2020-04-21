# koref
A coreference resolution engine based on Reconcile written in Kotlin.

## Getting Started

* ./gradlew build
* ./gradlew run - Run KorefDriver
* ./gradlew clean test - Run Unit Tests
* ./gradlew detekt - Linting
* ./gradlew jacocoTestReports - Generate Code Coverage
* ./gradlew jacocoTestCoverageVerification - Check if coverage meets specifications
* ./gradlew dokka - Generate Documentation

## Short term TODO

* Fix tests to pass on new install, currently looking for files on my machines
* Process raw text files and create tokens preprocessor.
* Use the reuters directory for SGML processing
* Process MUC documents and create tokens, raw text. 
* Process ACE documents and create tokens, raw text.
* Create part of speech, paragraphs, sentences tokens

## Roadmap to 1.0

* Read in a corpus and then apply preprocessors
    * Byte spans for all annotations
    * Support for multiple annotators
* Re-implement Reconcile Data Flow with key exceptions.
    * Client/Server Model
    * Modern and diverse Annotation Packages
    * Able to handle legacy and modern coreference data sets

