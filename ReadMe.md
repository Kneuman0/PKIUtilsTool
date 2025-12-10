# PKI Utils Tool

A collection of PKI (Public Key Infrastructure) utilities for certificate management and trust list operations.

## Building the Project

### Automated Build (Recommended)

The project includes automated dependency management that works cross-platform:

**Windows:**
```bash
cd BundleBreaker
mvnw.cmd clean package -Pbuild-deps
```

**Linux/Mac:**
```bash
cd BundleBreaker
./mvnw clean package -Pbuild-deps
```

The `build-deps` profile will automatically:
1. Clone or update all dependency repositories
2. Build dependencies in the correct order
3. Build the main BundleBreaker project
4. Create a fat JAR at `BundleBreaker/BB-0.jar`

### Manual Build

If you prefer to build manually, follow this order:

1. **TrustListDataModelLib** - https://github.com/Kneuman0/TrustListDataModelLib.git
2. **GUIUtils** - https://github.com/Kneuman0/GUIUtils.git  
3. **PKIUtilsLib** - https://github.com/Kneuman0/PKIUtilsLib.git
4. **BundleBreaker** (this project)

For each project, run:
```bash
./mvnw clean install -DskipTests
```

## Requirements

- Java 17 or higher
- Maven (or use the included Maven Wrapper)
- Git

## Project Structure

- **BundleBreaker** - Main application
- **build-dependencies.ps1** - Windows build script
- **build-dependencies.sh** - Linux/Mac build script
