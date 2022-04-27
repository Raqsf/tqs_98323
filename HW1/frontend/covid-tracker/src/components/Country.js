import { useState } from 'react';
import { Grid, InputLabel, MenuItem, FormControl, Select, Typography } from '@mui/material';

const Country = () => {
    const [country, setCountry] = useState('');
  
    const handleChange = (event) => {
      setCountry(event.target.value);
    };
  
    return (
        <>
        <Grid container alignItems="center" justifyContent="center" >
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
                    <MenuItem value={10}>Ten</MenuItem>
                    <MenuItem value={20}>Twenty</MenuItem>
                    <MenuItem value={30}>Thirty</MenuItem>
                </Select>
                </FormControl>
            </Grid>
        </Grid>
      </>
    );
}

export default Country;