import { Grid, Typography, Paper } from '@mui/material';
import { ThemeProvider } from '@mui/material/styles';
import { theme } from '../App.js';

const Stats = (props) => {
    /* console.log('================================' + props.name)
    console.log(props) */
    if(!(props // 👈 null and undefined check
        && Object.keys(props).length === 0
        && Object.getPrototypeOf(props) === Object.prototype)
    ){
    return (
        <>
            <ThemeProvider theme={theme}>
            <Grid container alignItems="center" justifyContent="center"> 
                <Grid item container direction="column" xs={3} textAlign='center'>
                    <Paper sx={{display: 'flex', flexDirection: 'column', p: 2, m: 1, bgcolor: "primary.lightBlue"}} >
                        <Typography variant="h6">Country's info</Typography>
                        <Typography variant="p"><b>Name:</b> {props.name}</Typography>
                        {props.continent !== "" ? <Typography variant="p"><b>Continent:</b> {props.continent}</Typography> : ""}
                        {props.population !== -1 ? <Typography variant="p"><b>Population:</b> {props.population}</Typography>: ""}
                    </Paper>
                </Grid>
                <Grid item container direction="column" xs={3} textAlign='center'>
                    <Paper sx={{display: 'flex', flexDirection: 'column', p: 2, m: 1, bgcolor: "primary.orange"}} >
                    <Typography variant="h6">Cases</Typography>
                    {props.cases.newCases != -1 ? <Typography variant="p"><b>New:</b> {props.cases.newCases}</Typography> : ""}
                    {props.cases.activeCases != -1 ? <Typography variant="p"><b>Active:</b> {props.cases.activeCases}</Typography> : ""}
                    {props.cases.criticalCases != -1 ? <Typography variant="p"><b>Critical:</b> {props.cases.criticalCases}</Typography> : ""}
                    {props.cases.recoveredCases != -1 ? <Typography variant="p"><b>Recovered:</b> {props.cases.recoveredCases}</Typography> : ""}
                    {props.cases.oneMPop != -1 ? <Typography variant="p"><b>Per 1 million pop:</b> {props.cases.oneMPop}</Typography> : ""}
                    {props.cases.total != -1 ? <Typography variant="p"><b>Total:</b> {props.cases.total}</Typography> : ""}
                    </Paper>
                </Grid>
                <Grid item container direction="column" xs={3} textAlign='center'>
                    <Paper sx={{display: 'flex', flexDirection: 'column', p: 2, m: 1, bgcolor: "primary.main"}} >
                    <Typography variant="h6">Deaths</Typography>
                    {props.deaths.newDeaths !== -1 ? <Typography variant="p"><b>New:</b> {props.deaths.newDeaths}</Typography> : ""}
                    {props.deaths.oneMPop !== -1 ? <Typography variant="p"><b>Per 1 million pop:</b> {props.deaths.oneMPop}</Typography> : ""}
                    {props.deaths.total !== -1 ? <Typography variant="p"><b>Total:</b> {props.deaths.total}</Typography> : ""}
                    </Paper>
                </Grid>
                <Grid item container direction="column" xs={3} textAlign='center'>
                    <Paper sx={{display: 'flex', flexDirection: 'column', p: 2, m: 1, bgcolor: "primary.pink"}} >
                    <Typography variant="h6">Tests</Typography>
                    {props.tests.oneMPop !== -1 ? <Typography variant="p"><b>Per 1 million pop:</b> {props.tests.oneMPop}</Typography> : ""}
                    {props.tests.total !== -1 ? <Typography variant="p"><b>Total:</b> {props.tests.total}</Typography> : ""}
                    </Paper>
                </Grid>
            </Grid>
            </ThemeProvider>
        </>
    );
    }
}

export default Stats;