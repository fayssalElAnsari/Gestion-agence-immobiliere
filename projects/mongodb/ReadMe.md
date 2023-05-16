
# System Design Process
1. define the basic functionality
2. ...

# Basic functionality
* CRUD operations on the API:
  * managing an apartment as follows:
    * Create a new apartment listing:
      * Endpoint: POST /apartments
      * Body: The request body would contain the details of the apartment to be created. This might include fields like title, description, price, location, number_of_bedrooms, number_of_bathrooms, owner_id, etc.

    * Get a list of all apartment listings:
      * Endpoint: GET /apartments
      * This would return a list of all the apartments in the database. You might implement pagination to return only a certain number of apartments at a time, and allow the client to request more as needed.

    * Get a specific apartment listing:
      * Endpoint: GET /apartments/{apartment_id}
      * This would return the details of a specific apartment, identified by its apartment_id.

    * Update a specific apartment listing:
      * Endpoint: PUT /apartments/{apartment_id}
      * Body: The request body would contain the fields of the apartment to be updated and their new values.

    * Delete a specific apartment listing:
      * Endpoint: DELETE /apartments/{apartment_id}
      * This would delete the apartment with the given apartment_id.