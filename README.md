# FoodPantryApp

McFarland Community Food Pantry data management application.

This is a JavaFX desktop application requiring Java 11 or above.

Data is stored in CSV files that may be manually edited while the program is running.

Reports can be generated for display or exported to Excel format.

## Functionality

- Manage customers
- Manage customer visits
- Manage volunteers
- Manage inventory/donations
- Summarize inventory/donations per month for reporting
- Summarize customer visits per month for reporting
- Summarize volunteer hours per month for reporting

## Developer Information

All UI functionality is managed by loading an FXML file that references a specific controller.

Almost all screens are intended to display tabular data. Boilerplate for this has been abstracted into `AbstractController`.

Reports are also tabular in nature. `GenericReportController` manages the generic report container for all reports. A report-specific `AbstractReportStrategy` is supplied to the controller to customize the display and provide report-specific data.

Input validation is managed via a series of classes in `org.pantry.food.ui.validation`. A `ValidateStatusTracker` checks to see if a given validation passes and updates the UI input control's border to red/not red depending on whether the validation was successful.

CSV files are read by DAOs in `org.pantry.food.dao`. Each DAO extends `AbstractCsvDao`, which abstracts the boilerplate of opening the file, reading it, mapping its data, and closing it. Each DAO acts as a template supplying DAO-specific information to AbstractCsvDao so it can properly read a specific file. Row mappers are used to translate each row of the CSV file to an model object.

### Building a Distributable Jar

Run `mvn package` to create a distributable fatjar. The jar includes the JFX runtime. The generated jar is located at <project root>/target.