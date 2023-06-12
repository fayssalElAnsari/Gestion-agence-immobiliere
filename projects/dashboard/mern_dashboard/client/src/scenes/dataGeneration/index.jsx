import React, { useState } from "react";
import Button from "@mui/material/Button";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import TextField from "@mui/material/TextField";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";

const DataGeneration = () => {
  {
    const [collection, setCollection] = useState("");
    const [numberOfDocuments, setNumberOfDocuments] = useState("");

    const collections = ["users", "apartments", "reservations"];

    const handleSubmit = async (event) => {
      event.preventDefault();

      const response = await fetch(
        `http://localhost:8081/api/mockdata/${collection}`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ numberOfDocuments }),
        }
      );

      if (response.ok) {
        // Handle success
      } else {
        // Handle error
      }
    };

    return (
      <form onSubmit={handleSubmit}>
        <FormControl fullWidth>
          <InputLabel id="collection-select-label">Collection</InputLabel>
          <Select
            labelId="collection-select-label"
            id="collection-select"
            value={collection}
            onChange={(event) => setCollection(event.target.value)}
            autoWidth
          >
            {collections.map((name) => (
              <MenuItem key={name} value={name}>
                {name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <TextField
          type="number"
          label="Number of Documents"
          value={numberOfDocuments}
          onChange={(event) =>
            setNumberOfDocuments(parseInt(event.target.value))
          }
        />
        <Button type="submit" variant="contained" color="primary">
          Submit
        </Button>
      </form>
    );
  }
};

export default DataGeneration;
