import React, { useState } from "react";
import Button from "@mui/material/Button";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import TextField from "@mui/material/TextField";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import { Box } from "@mui/system";
import { Typography } from "@mui/material";
import axios from "axios";

const DataGeneration = () => {
  {
    const [collection, setCollection] = useState("");
    const [numberOfDocuments, setNumberOfDocuments] = useState("");

    const collections = [
      "users",
      "apartments",
      "reservations",
      "clients",
      "transactions",
    ].sort();

    const generateData = async (event) => {
      event.preventDefault();

      try {
        const response = await axios.post(
          `http://localhost:8081/api/mockdata/${collection}`,
          { numberOfDocuments }
        );

        // Handle success
        console.log(response.data);
      } catch (error) {
        // Handle error
        console.error(error);
      }
    };

    const handleDelete = async (event) => {
      event.preventDefault();

      try {
        // Fetch all documents in the collection
        const { data: docs } = await axios.get(
          `http://localhost:8081/api/${collection}`
        );
        console.log(docs);

        // Delete each document in the collection
        for (const doc of docs) {
          await axios.delete(
            `http://localhost:8081/api/${collection}/${doc.id}`
          );
        }

        alert("All documents deleted successfully!");
      } catch (error) {
        console.error(error);
      }
    };

    return (
      <Box>
        <Typography variant="h3">Data Generation</Typography>
        <form onSubmit={generateData}>
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
            Generate
          </Button>
        </form>

        <Typography variant="h3">Data Deletion</Typography>
        <form onSubmit={handleDelete}>
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
          <Button type="submit" variant="contained" color="primary">
            Delete
          </Button>
        </form>
      </Box>
    );
  }
};

export default DataGeneration;
