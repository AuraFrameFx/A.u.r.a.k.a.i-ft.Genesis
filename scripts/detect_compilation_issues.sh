#!/bin/bash

# ROM Tools Compilation Issue Detection Script
# Detects common compilation error patterns in the codebase

echo "========================================="
echo "ROM Tools Compilation Issue Detector"
echo "========================================="
echo ""

# Colors for output
RED='\033[0;31m'
YELLOW='\033[1;33m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
APP_SRC="$PROJECT_ROOT/app/src/main/java"

echo "Project root: $PROJECT_ROOT"
echo "Scanning: $APP_SRC"
echo ""

# Counter variables
TOTAL_ISSUES=0

# 1. Check for incorrect model imports
echo "${YELLOW}[1] Checking for problematic .models.models.* imports...${NC}"
MODEL_IMPORTS=$(grep -r "import.*\.models\.models\." "$APP_SRC" 2>/dev/null | wc -l)
if [ "$MODEL_IMPORTS" -gt 0 ]; then
    echo "${RED}   Found $MODEL_IMPORTS files with .models.models.* imports${NC}"
    grep -r "import.*\.models\.models\." "$APP_SRC" --include="*.kt" -n | head -10
    TOTAL_ISSUES=$((TOTAL_ISSUES + MODEL_IMPORTS))
else
    echo "${GREEN}   No problematic .models.models.* imports found${NC}"
fi
echo ""

# 2. Check for DELETED files that should be removed
echo "${YELLOW}[2] Checking for DELETED_ files...${NC}"
DELETED_FILES=$(find "$APP_SRC" -name "DELETED_*.kt" 2>/dev/null | wc -l)
if [ "$DELETED_FILES" -gt 0 ]; then
    echo "${RED}   Found $DELETED_FILES DELETED_ files that should be removed:${NC}"
    find "$APP_SRC" -name "DELETED_*.kt"
    TOTAL_ISSUES=$((TOTAL_ISSUES + DELETED_FILES))
else
    echo "${GREEN}   No DELETED_ files found${NC}"
fi
echo ""

# 3. Check for duplicate class declarations
echo "${YELLOW}[3] Checking for potential duplicate class declarations...${NC}"

# Check FileMetadata
FM_COUNT=$(grep -r "^data class FileMetadata\|^class FileMetadata" "$APP_SRC" --include="*.kt" | wc -l)
if [ "$FM_COUNT" -gt 1 ]; then
    echo "${RED}   FileMetadata declared $FM_COUNT times:${NC}"
    grep -r "^data class FileMetadata\|^class FileMetadata" "$APP_SRC" --include="*.kt"
    TOTAL_ISSUES=$((TOTAL_ISSUES + FM_COUNT - 1))
fi

# Check EncryptionStatus
ES_COUNT=$(grep -r "^class EncryptionStatus\|^sealed class EncryptionStatus" "$APP_SRC" --include="*.kt" | wc -l)
if [ "$ES_COUNT" -gt 1 ]; then
    echo "${RED}   EncryptionStatus declared $ES_COUNT times:${NC}"
    grep -r "^class EncryptionStatus\|^sealed class EncryptionStatus" "$APP_SRC" --include="*.kt"
    TOTAL_ISSUES=$((TOTAL_ISSUES + ES_COUNT - 1))
fi

# Check OracleDriveApi
ODA_COUNT=$(grep -r "^interface OracleDriveApi\|^annotation class OracleDriveApi" "$APP_SRC" --include="*.kt" | wc -l)
if [ "$ODA_COUNT" -gt 1 ]; then
    echo "${RED}   OracleDriveApi declared $ODA_COUNT times:${NC}"
    grep -r "^interface OracleDriveApi\|^annotation class OracleDriveApi" "$APP_SRC" --include="*.kt"
    TOTAL_ISSUES=$((TOTAL_ISSUES + ODA_COUNT - 1))
fi

if [ "$FM_COUNT" -le 1 ] && [ "$ES_COUNT" -le 1 ] && [ "$ODA_COUNT" -le 1 ]; then
    echo "${GREEN}   No obvious duplicate declarations found${NC}"
fi
echo ""

# 4. Check for common missing imports
echo "${YELLOW}[4] Checking for files importing from potentially missing packages...${NC}"

# Check for imports that might not exist
MISSING_IMPORTS=0

# Check for models imports
grep -r "import.*aura.*\.models\." "$APP_SRC" --include="*.kt" > /tmp/model_imports.txt 2>/dev/null
if [ -s /tmp/model_imports.txt ]; then
    echo "${YELLOW}   Found aura.models imports (verify these packages exist):${NC}"
    cat /tmp/model_imports.txt | cut -d: -f1 | sort -u | head -5
    MISSING_IMPORTS=$((MISSING_IMPORTS + $(cat /tmp/model_imports.txt | wc -l)))
fi

if [ "$MISSING_IMPORTS" -eq 0 ]; then
    echo "${GREEN}   No suspicious model imports found${NC}"
fi
echo ""

# 5. Check for files with high likelihood of errors
echo "${YELLOW}[5] Identifying high-risk files (many complex imports)...${NC}"

# Files with 10+ import statements (more likely to have issues)
HIGH_IMPORT_FILES=$(find "$APP_SRC" -name "*.kt" -exec sh -c 'grep -c "^import " "$1" | awk -v f="$1" "{if (\$1 >= 10) print f}"' _ {} \; | head -10)

if [ -n "$HIGH_IMPORT_FILES" ]; then
    echo "${YELLOW}   Files with many imports (higher risk of import errors):${NC}"
    echo "$HIGH_IMPORT_FILES"
else
    echo "${GREEN}   No high-risk import files identified${NC}"
fi
echo ""

# 6. Check for absolute path references (Windows)
echo "${YELLOW}[6] Checking for hardcoded Windows paths...${NC}"
WIN_PATHS=$(grep -r "C:/" "$PROJECT_ROOT" --include="*.kt" --include="*.gradle*" 2>/dev/null | wc -l)
if [ "$WIN_PATHS" -gt 0 ]; then
    echo "${RED}   Found $WIN_PATHS files with Windows absolute paths:${NC}"
    grep -r "C:/" "$PROJECT_ROOT" --include="*.kt" --include="*.gradle*" -n | head -5
    TOTAL_ISSUES=$((TOTAL_ISSUES + WIN_PATHS))
else
    echo "${GREEN}   No hardcoded Windows paths found${NC}"
fi
echo ""

# Summary
echo "========================================="
echo "Summary"
echo "========================================="
echo "Total potential issues detected: $TOTAL_ISSUES"
echo ""

if [ "$TOTAL_ISSUES" -eq 0 ]; then
    echo "${GREEN}No critical issues detected! ✓${NC}"
    exit 0
else
    echo "${RED}Issues found that may cause compilation errors.${NC}"
    echo "See ROM_TOOLS_COMPILATION_FIX.md for detailed fix strategies."
    exit 1
fi
