# URL Shortener Service

A simple URL shortener service built with Spring Boot and Kotlin.

## Features

- Shorten long URLs to short, random 6-character keys
- Redirect from short URLs to original URLs
- In-memory storage of URL mappings
- Comprehensive logging

## API Endpoints

### Shorten a URL
```
POST /api/shorten
```
**Request Body:**
```json
{
  "url": "https://www.example.com/some/very/long/url"
}
```
**Response:**
```json
{
  "shortenedUrl": "http://localhost:8080/api/shorten/abC12d",
  "shortKey": "abC12d"
}
```

### Get Original URL
```
GET /api/original/{shortKey}
```
**Response:**
```json
{
  "originalUrl": "https://www.example.com/some/very/long/url"
}
```

### Redirect to Original URL
```
GET /api/shorten/{shortKey}
```
**Response:**
- Redirects (HTTP 302) to the original URL

## Testing

Use the included `http-client.http` file for manual testing with the IntelliJ HTTP Client.
