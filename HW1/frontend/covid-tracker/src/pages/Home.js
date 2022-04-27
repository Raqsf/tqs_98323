import * as React from 'react';
import { Grid, Typography, Paper } from '@mui/material';
import { ThemeProvider } from '@mui/material/styles';
import { theme } from '../App.js';
import Country from '../components/Country';

const Home = () => {

    return (
        <>
            <ThemeProvider theme={theme}>
                <Grid
                    container
                    spacing={1}
                    direction="column"
                    alignItems="center"
                    justifyContent="center"
                    style={{ minHeight: '50vh', backgroundColor:"#E27D60" }}
                    rowSpacing={1}
                >

                    <Grid item xs={3}>
                        <Typography
                            variant="h2"
                            noWrap
                            component="div"
                            style={{ color: "white" }}
                        >
                            COVID TRACKER
                        </Typography>
                    </Grid>     
                </Grid> 
            </ThemeProvider>

            <Country />

            <Grid 
                container 
                spacing={1} 
                direction="column"
                alignItems="center"
                justifyContent="center"
                style={{ padding: 50 }}
            >
                <Grid item xs={1} /* key={index} */ textAlign='center'>
                    <Paper 
                        elevation={2} 
                        style={{ minWidth: '50vw' }}
                    >
                        <Typography variant="h6" component="span">TODAY'S INFO</Typography>
                    </Paper>
                </Grid>
            </Grid>
        </>
    );
}

export default Home;