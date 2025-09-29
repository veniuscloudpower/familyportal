# Azure Blob Storage Setup

This application now uses Azure Blob Storage for file attachments instead of storing them as Base64 in the database.

## Environment Variables

Set the following environment variables before running the application:

```bash
# Azure Storage Account Connection String
export AZURE_STORAGE_CONNECTION_STRING="DefaultEndpointsProtocol=https;AccountName=YOUR_ACCOUNT_NAME;AccountKey=YOUR_ACCOUNT_KEY;EndpointSuffix=core.windows.net"

# Azure Storage Container Name (will be created automatically if it doesn't exist)
export AZURE_STORAGE_CONTAINER_NAME="attachments"
```

## Azure Setup Steps

1. **Create an Azure Storage Account**:
   - Go to Azure Portal
   - Create a new Storage Account
   - Note the account name and access key

2. **Get Connection String**:
   - In your storage account, go to "Access keys"
   - Copy the connection string

3. **Set Environment Variables**:
   - Set `AZURE_STORAGE_CONNECTION_STRING` with your connection string
   - Set `AZURE_STORAGE_CONTAINER_NAME` (default: "attachments")

## Database Migration

The application will automatically handle the database schema change from `fileData` to `blobUrl`. However, existing attachments stored as Base64 will need to be migrated manually if needed.

## Features

- **Upload**: Files are uploaded to Azure Blob Storage with unique names
- **Download**: Files are streamed directly from Azure Blob Storage
- **Delete**: Files are removed from both database and blob storage
- **Security**: Only blog post owners can delete attachments

## Testing

To test locally without Azure, the application will fail gracefully if the connection string is invalid, but you should set up a real Azure Storage Account for proper testing.