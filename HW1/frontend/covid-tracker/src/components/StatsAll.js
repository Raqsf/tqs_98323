import { Grid, Typography, Paper } from '@mui/material';
import { ThemeProvider } from '@mui/material/styles';
import { theme } from '../App.js';

const StatsAll = (props) => {

    const getResultInt = (arg, text) => {
        return arg !== -1 ? <Typography variant="p"><b>{text}:</b> {arg}</Typography> : "";
    }

    const getResultDouble = (arg, text) => {
        return arg !== -1.0 ? <Typography variant="p"><b>{text}:</b> {arg}</Typography> : "";
    }

    if(!(props // 👈 null and undefined check
        && Object.keys(props).length === 0
        && Object.getPrototypeOf(props) === Object.prototype)
    ){
    return (
        <>
            <ThemeProvider theme={theme}>
            <Grid container alignItems="center" justifyContent="center"> 
                <Grid item container direction="column" xs={4} textAlign='center'>
                    <Paper sx={{display: 'flex', flexDirection: 'column', p: 2, m: 1, bgcolor: "primary.orange"}} >
                    <Typography variant="h6">Cases</Typography>
                    {getResultInt(props.cases.newCases, "New")}
                    {getResultInt(props.cases.activeCases, "Active")}
                    {getResultInt(props.cases.criticalCases, "Critical")}
                    {getResultInt(props.cases.recovered, "Recovered")}
                    {getResultDouble(props.cases.oneMPop, "Per 1 million pop")}
                    {getResultInt(props.cases.total, "Total")}
                    </Paper>
                </Grid>
                <Grid item container direction="column" xs={4} textAlign='center'>
                    <Paper sx={{display: 'flex', flexDirection: 'column', p: 2, m: 1, bgcolor: "primary.main"}} >
                    <Typography variant="h6">Deaths</Typography>
                    {getResultInt(props.deaths.newDeaths, "New")}
                    {getResultDouble(props.deaths.oneMPop, "Per 1 million pop")}
                    {getResultInt(props.deaths.total, "Total")}
                    </Paper>
                </Grid>
            </Grid>
            </ThemeProvider>
        </>
    );
    }
}

export default StatsAll;