# Zelda API

Welcome to the Zelda API! This API is under development for Zelda Mobile (SwiftUI Project) and provides information about various elements in the Legend of Zelda series.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Contributing](#contributing)
- [License](#license)

## Introduction
The Zelda API allows users to access data about characters, items, locations, and more from the Legend of Zelda universe.

## Features
- Retrieve information about characters
- Get details on items and equipment
- Access location data (So on)
- Search functionality (So on)

## Installation
To install the Zelda API, clone the repository and install the dependencies:

```bash
mvn clean install
```

## Usage
Provide environmental variables such as SECRET_KEY, MONGODB_HOST
The API will be available at `http://localhost:8080`.

## Endpoints
- `GET /characters` - Retrieve a list of characters
- `GET /items` - Retrieve paged list of items.
- `GET /dungeons` - Retrieve paged list of dungeons.
- `GET /images/**` - Show image.
- `GET /games` - Retrieve all Zelda games until now.
- `POST /games/add-favorite` - Add your favorite Zelda games.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.