# koref
![Build & Lint](https://github.com/nathan-gilbert/koref/workflows/Build%20&%20Lint/badge.svg?branch=master)

A coreference resolution engine based on Reconcile written in Kotlin.

## Getting Started

- ./gradlew build
- ./gradlew run - Run KorefDriver
- ./gradlew clean test - Run Unit Tests
- ./gradlew detekt - Linting
- ./gradlew jacocoTestReports - Generate Code Coverage
- ./gradlew jacocoTestCoverageVerification - Check if coverage meets specifications
- ./gradlew dokkaHtml - Generate Documentation

## Short term TODO

- Need script to download models
- Need to make tests runnable via the cloud
- Add stanford sentences, tags, tests
- Add logging
- Use the reuters directory for SGML processing
- Process MUC documents and create tokens, raw text.
- Process ACE documents and create tokens, raw text.
- Create part of speech, paragraphs, dependency, grammar annotations

## Roadmap to 1.0

- Read in a corpus and then apply preprocessors
    - Byte spans for all annotations
    - Support for multiple annotators
- Re-implement Reconcile Data Flow with some exceptions.
    - Client/Server Model
    - Modern and diverse Annotation Packages
    - Able to handle legacy and modern coreference data sets
