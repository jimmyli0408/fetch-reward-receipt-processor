# Fetch Reward Receipt Processor

A Spring Boot-based REST API for processing receipts and calculating reward points based on specified rules. The application is containerized using Docker for easy deployment.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)

## Overview
This project is designed to handle receipt processing and calculate reward points based on a set of rules from [requirements](https://github.com/fetch-rewards/receipt-processor-challenge/blob/main/README.md). It includes endpoints to process receipts and retrieve the calculated points. The project uses Spring Boot for backend development and is packaged using Docker for ease of deployment.

## Features
- **Process Receipts**: Accepts receipt data in JSON format and returns a unique ID for each receipt.
- **Calculate Reward Points**: Uses predefined rules to calculate points for each receipt by unique ID.

## Technologies Used
- **Java**: Core programming language
- **Spring Boot**: Framework for building the REST API
- **Docker**: Containerization platform for deployment
- **Maven**: Build and dependency management
- **JUnit 5**: Unit testing framework
- **Lombok**: Reduces boilerplate code

## Getting Started

### Prerequisites
- **Java 17** or later
- **Maven 3.8.1** or later
- **Docker** for containerization

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/jimmyli0408/fetch-reward-receipt-processor.git
   cd fetch-reward-reciept-processor
   ```

### Running the Application
1. Make sure the JAR file is built:
   ```bash
   mvn clean package
   ```
2. Build the Docker image:
   ```bash
   docker build -t fetch-reward-receipt-processor .
   ```
3. Run the container:
   ```bash
   docker run -p 8080:8080 --name fetch-reward-app fetch-reward-receipt-processor
   ```

## API Endpoints

### 1. Process Receipts
- **Endpoint**: `/receipts/process`
- **Method**: `POST`
- **Request Body**: JSON receipt data
- **Response**: Unique ID for the receipt
- **Example Request**:
  ```json
  {
    "retailer": "M&M Corner Market",
    "purchaseDate": "2022-03-20",
    "purchaseTime": "14:33",
    "items": [
      {
        "shortDescription": "Gatorade",
        "price": "2.25"
      },{
        "shortDescription": "Gatorade",
        "price": "2.25"
      },{
        "shortDescription": "Gatorade",
        "price": "2.25"
      },{
        "shortDescription": "Gatorade",
        "price": "2.25"
      }
    ],
    "total": "9.00"
  }
  ```
### 2. Get Points
- **Endpoint**: `/receipts/{id}/points`
- **Method**: `GET`
- **Response**: Points awarded for the receipt
- Example Response:
  ```json
  {
    "points": 109
  }

## Testing
- Run unit tests using Maven:
  ```bash
  mvn test
  ```



   


