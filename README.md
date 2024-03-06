# My Quarkus Application

This document provides instructions on how to start the Quarkus application using `docker-compose` and access its features.

## Starting the Application

To start the application and its dependencies (such as the database and Traefik reverse proxy), navigate to the root directory of the project where the `docker-compose.yml` file is located, and run the following command:

```bash
docker-compose up -d
```

This command will start all the services defined in `docker-compose.yml` in detached mode.

## Accessing the Application

The application is placed behind Traefik, which acts as a reverse proxy and load balancer. Traefik manages the incoming requests and routes them to the appropriate service based on the configuration.

- **Application URL**: [http://localhost/app](http://localhost/app)

  Access the main entry point of the application using this URL. Traefik routes the requests to the Quarkus application, ensuring seamless access.

## OpenAPI and Swagger UI

The application provides an OpenAPI specification, making it easy to understand and interact with the API.

- **OpenAPI Specification**: [http://localhost/app/openapi](http://localhost/app/openapi)

  This URL serves the raw OpenAPI specification in YAML format, providing detailed information about the API endpoints, parameters, and responses.


## Important Notes

- Ensure Docker and Docker Compose are installed and running on your machine before executing the `docker-compose up -d` command.
- The application and all related services are defined in the `docker-compose.yml` file. Review this file for details about the service configurations, networks, and volumes.
- Traefik's dashboard can be accessed at [http://localhost:8085](http://localhost:8085) to view the routing rules, services, and middlewares managed by Traefik.
- Make sure to stop and remove the containers when they are no longer needed by running `docker-compose down` to free up resources.
