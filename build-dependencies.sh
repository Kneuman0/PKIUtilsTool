#!/bin/bash
# Build script for PKIUtilsTool dependencies only
# This script is called by Maven during the build process

set -e

BASE_DIR="${HOME}/Coding"
MAIN_PROJECT="${BASE_DIR}/PKIUtilsTool/BundleBreaker"
LOG_FILE="${BASE_DIR}/PKIUtilsTool/build-dependencies.log"

# Repository URLs
declare -A REPOS=(
    ["GUIUtils"]="https://github.com/Kneuman0/GUIUtils.git"
    ["TrustListDataModelLib"]="https://github.com/Kneuman0/TrustListDataModelLib.git"
    ["PKIUtilsLib"]="https://github.com/Kneuman0/PKIUtilsLib.git"
)

# Build order (dependencies first)
BUILD_ORDER=("GUIUtils" "TrustListDataModelLib" "PKIUtilsLib")

# Function to write log messages
write_log() {
    local message="$1"
    local color="${2:-white}"
    
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    local log_message="[$timestamp] $message"
    
    case "$color" in
        cyan) echo -e "\033[0;36m$message\033[0m" ;;
        yellow) echo -e "\033[0;33m$message\033[0m" ;;
        green) echo -e "\033[0;32m$message\033[0m" ;;
        red) echo -e "\033[0;31m$message\033[0m" ;;
        *) echo "$message" ;;
    esac
    
    if [ "${DEBUG:-false}" = "true" ]; then
        echo "$log_message" >> "$LOG_FILE"
    fi
}

# Initialize log file if debug is enabled
if [ "${DEBUG:-false}" = "true" ]; then
    echo "=== Build Dependencies Log ===" > "$LOG_FILE"
    write_log "Debug logging enabled. Writing to: $LOG_FILE" "cyan"
fi

write_log "Building dependencies for BundleBreaker..." "cyan"

# Clone or update repositories
for repo_name in "${!REPOS[@]}"; do
    repo_path="${BASE_DIR}/${repo_name}"
    repo_url="${REPOS[$repo_name]}"
    
    if [ -d "$repo_path" ]; then
        write_log "Updating $repo_name..." "yellow"
        cd "$repo_path"
        git pull
    else
        write_log "Cloning $repo_name..." "yellow"
        cd "$BASE_DIR"
        git clone "$repo_url"
    fi
done

# Copy Maven wrapper to each dependency project
write_log "Setting up Maven wrapper for dependencies..." "yellow"
for repo_name in "${BUILD_ORDER[@]}"; do
    repo_path="${BASE_DIR}/${repo_name}"
    if [ -f "${MAIN_PROJECT}/mvnw" ]; then
        cp "${MAIN_PROJECT}/mvnw" "$repo_path/" 2>/dev/null || true
        chmod +x "${repo_path}/mvnw" 2>/dev/null || true
    fi
    if [ -d "${MAIN_PROJECT}/.mvn" ]; then
        cp -r "${MAIN_PROJECT}/.mvn" "$repo_path/" 2>/dev/null || true
    fi
done

# Build each dependency in order
for repo_name in "${BUILD_ORDER[@]}"; do
    repo_path="${BASE_DIR}/${repo_name}"
    
    write_log "Building $repo_name..." "green"
    
    cd "$repo_path"
    
    # Use mvnw if available, otherwise use mvn
    if [ -f "./mvnw" ]; then
        ./mvnw clean install -DskipTests
    else
        mvn clean install -DskipTests
    fi
    
    exit_code=$?
    
    if [ "${DEBUG:-false}" = "true" ]; then
        write_log "Maven exit code: $exit_code" "yellow"
    fi
    
    if [ $exit_code -ne 0 ]; then
        write_log "ERROR: $repo_name build failed! Exit code: $exit_code" "red"
        exit 1
    fi
    
    write_log "$repo_name built successfully!" "green"
done

write_log "All dependencies built successfully!" "green"
