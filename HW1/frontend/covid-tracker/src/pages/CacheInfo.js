import { useState, useEffect }from 'react';
import { 
    Grid, 
    Typography, 
    Paper, 
    Box, 
    LinearProgress, 
    Table, 
    TableBody, 
    TableCell, 
    TableContainer, 
    TableHead, 
    TableRow
} from '@mui/material';
import { ThemeProvider } from '@mui/material/styles';
import { theme } from '../App.js';
import axios from 'axios';

const CacheInfo = () => {
    const [rows, setRows] = useState([]);
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(() => {
        axios.get(`/cache/details`)
        .then(res => {
            setIsLoaded(true);
            setRows(res.data);
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
                    
                    <Grid item xs={3}>
                        <Typography
                            variant="h6"
                            noWrap
                            component="div"
                            style={{ color: "white" }}
                        >
                            Cache Info
                        </Typography>  
                    </Grid> 
                </Grid> 
            </ThemeProvider>

            <Grid 
                container 
                direction="column"
                alignItems="center"
                justifyContent="center"
                style={{ padding: 50 }}
            >
                <Grid item xs={1} textAlign='center'>
                    <TableContainer 
                        component={Paper} elevation={2} 
                        style={{ minWidth: '75vw', padding: 8, backgroundColor: "#F8F2E9" }}
                    >
                        <Table sx={{ minWidth: 650 }} aria-label="a dense table">
                            <TableHead>
                            <TableRow>
                                <TableCell align="center">Hits</TableCell>
                                <TableCell align="center">Misses</TableCell>
                                <TableCell align="center">Hit Ratio</TableCell>
                                <TableCell align="center">Requests</TableCell>
                                <TableCell align="center">Size</TableCell>
                            </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableRow
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                <TableCell align="center">{rows.hits}</TableCell>
                                <TableCell align="center">{rows.misses}</TableCell>
                                <TableCell align="center">{rows.hitRatio}</TableCell>
                                <TableCell align="center">{rows.requests}</TableCell>
                                <TableCell align="center">{rows.size}</TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Grid>
            </Grid>
        </>
    );
    }
}

export default CacheInfo;