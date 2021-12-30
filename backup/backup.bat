echo off
echo 'Generate backup file name'

set BACKUP_FILE=%date%.database.backup

echo 'Backup path: %BACKUP_FILE%'
echo 'Creating a backup ...'

set PGPASSWORD=postgres
"C:\Program Files\PostgreSQL\14\bin\pg_dump.exe" --username="postgres" -d postgres --format=custom -f "%BACKUP_FILE%"

echo 'Backup successfully created: %BACKUP_FILE%'