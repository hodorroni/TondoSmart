***Dynamic Form from JSON Schema***

**Overview**

Android app that renders a dynamic form based on a JSON Schema definition and validates user input accordingly.

For this assignment, the schema is hardcoded in the project but structured as if it were received from a backend.

**How to Run**

Open in Android Studio

Sync Gradle

Run on emulator or device

**What’s Implemented**

Dynamic form rendering from JSON data Schema and for the UI, JSON UI Schema

Supported field types: text, number, boolean, dropdown (enum)

Validation: required, min/max, minLength/maxLength

Submit behavior:

Valid → success message + logs generated JSON payload

Invalid → field-level error messages

MVVM architecture with clean state management

Sealed FormField models for dynamic rendering