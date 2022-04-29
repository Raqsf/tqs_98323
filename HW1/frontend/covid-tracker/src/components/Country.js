import { useState, useEffect } from 'react';
import { Grid, InputLabel, MenuItem, FormControl, Select, Typography, LinearProgress, Box } from '@mui/material';
import axios from 'axios';

const Country = (props) => {
    const [country, setCountry] = useState('');
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
  
    const handleChange = (event) => {
      setCountry(event.target.value);
      props.onSelectCountry(event.target.value);
    };

    useEffect(() => {
        axios.get("/countries")
        .then(res => {
            setIsLoaded(true);
            setItems(res.data);
          })
        .catch(err => {
            setIsLoaded(true);
            setError(err);
        })
    }, []);

    if (error) {
        return (
        <>
            <Grid container alignItems="center" justifyContent="center" sx={{ p: 3 }}> 
                <Grid item xs={3} textAlign='center'>
                    <Box style={{ padding: 40 }}>
                        <Typography variant="p">
                            Error: {error.message}
                        </Typography>
                    </Box>
                </Grid> 
            </Grid>
        </>
        );
    } else if (!isLoaded) {
        return (
        <>
            <Grid container alignItems="center" justifyContent="center" sx={{ p: 3 }}> 
                <Grid item xs={3} textAlign='center'>
                    <Box style={{ padding: 40 }}>
                        <Typography variant="p">Loading ...</Typography>
                        <LinearProgress />
                    </Box>
                </Grid> 
            </Grid>
        </>
        );
    } else {
  
        return (
        <>
        <Grid container alignItems="center" justifyContent="center" sx={{ p: 3 }}>
            <Grid item xs={3} textAlign='center'>
                <Typography variant="h6">Choose a country</Typography>
            </Grid>
            <Grid item xs={3} textAlign='center'>
                <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }} >
                <InputLabel id="demo-simple-select-label">Country</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={country}
                    label="Country"
                    onChange={handleChange}
                >
                    <MenuItem value="Global">Global</MenuItem>
                    {items.map(item => (
                        <MenuItem value={item} key={item}>{item}</MenuItem>
                    ))}
                </Select>
                </FormControl>
            </Grid>
        </Grid>
      </>
    );

    }
}

export default Country;