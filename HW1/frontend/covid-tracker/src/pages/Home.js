import { useState, useEffect }from 'react';
import { Grid, Typography, Paper, Box, LinearProgress, Button, TextField } from '@mui/material';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { ThemeProvider } from '@mui/material/styles';
import { theme } from '../App.js';
import Country from '../components/Country';
import Stats from '../components/Stats';
import axios from 'axios';

const Home = () => {
    const [country, setCountry] = useState('');
    const [isSelected, setIsSelected] = useState(false);
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [errorHist, setErrorHist] = useState(null);
    const [isLoadedHist, setIsLoadedHist] = useState(false);
    const [itemsHist, setItemsHist] = useState([]);
    const [limit, setLimit] = useState(5);
    const [date, setDate] = useState(null);


    const handleCountry = (countryName) => {
        if(countryName !== "Global") {
            setCountry(countryName);
        } else {
            setCountry('');
        }

        countryName !== "Global" ? setIsSelected(true) : setIsSelected(false);
        setIsLoaded(false);
      };

    const showMoreStats = () => {
        if((limit < items.length && items.length <= limit + 5) || limit + 5 < items.length) {
            setLimit(limit + 5);
        }
    }

    const showLessStats = () => {
        if(limit - 5 > 5) {
            setLimit(limit - 5);
        } else {
            setLimit(5);
        }
    }

    const isToday = (someDateString) => {
        const someDate = new Date(someDateString)
        const today = new Date()
        return someDate.getDate() === today.getDate() &&
          someDate.getMonth() === today.getMonth() &&
          someDate.getFullYear() === today.getFullYear()
    }

    const toDate = (someDateString) => {
        const time = new Date(someDateString);
        return time.toLocaleString();
    }

    const whereToSlice = () => {
        return limit > items.length ? items.length : limit;
    }

    const getHistory = () => {
        if(!isLoaded && !isLoadedHist) {
            return <LinearProgress />;
        }

        if(date == null) {
            return itemsHist.map((item,i) =>isToday(item.time) ?
            <Box key={i}> <Typography>{toDate(item.time)}</Typography><Stats key={i} {...item}/></Box>
            : "");
        }
        return itemsHist.map((item,i) => <Box key={i}> <Typography>{toDate(item.time)}</Typography><Stats key={i} {...item}/></Box>);
    } 

    useEffect(() => {
        axios.get(`/stats/${country}`)
        .then(res => {
            setIsLoaded(true);
            setItems(res.data);
        })
        .catch(err => {
            setIsLoaded(true);
            setError(err);
        })
    }, [country]);

    useEffect(() => {
        if(country.length > 0){
            const url = date == null ? `/history/${country}` : `/history/${country}?date=${date}`
            axios.get(url)
            .then(res => {
                setIsLoadedHist(true);
                setItemsHist(res.data);
            })
            .catch(err => {
                setIsLoadedHist(true);
                setErrorHist(err);
            })
        }
    }, [country, date]);

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
    
    } 

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
                            Statistics
                        </Typography>  
                    </Grid> 
                </Grid> 
            </ThemeProvider>

            <Country onSelectCountry={handleCountry}/>

            <Grid 
                container 
                spacing={1} 
                direction="column"
                alignItems="center"
                justifyContent="center"
                style={{ padding: 50 }}
                rowSpacing={10}
            >
                <Grid item xs={1} textAlign='center'>
                    <Paper 
                        elevation={2} 
                        style={{ minWidth: '75vw', padding: 20, backgroundColor: "#F8F2E9" }}
                    >
                        <Typography variant="h6" component="span">
                            CURRENT INFO
                        </Typography>
                        {!isLoaded ? <LinearProgress /> :
                            items.slice(0,whereToSlice()).map((item,i) => (<Box key={i}> <Stats {...item}/></Box>))
                        }
                        {isSelected ? 
                            "" : 
                            <Box style={{ paddingTop: 20 }}>
                                <Button onClick={showMoreStats}>Show More</Button> 
                                <Button onClick={showLessStats}>Show Less</Button>
                            </Box>
                        }
                    </Paper>
                </Grid>
                {isSelected && !errorHist ?  
                <Grid item xs={1} textAlign='center'>
                    <Paper 
                        elevation={2} 
                        style={{ minWidth: '75vw', padding: 20, backgroundColor: "#F8F2E9" }}
                    >
                        <Typography variant="h6" component="span">
                            HISTORY 
                        </Typography>
                        <Box sx={{ m: 2 }}>
                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                <DatePicker
                                    label="Choose a day"
                                    value={date}
                                    maxDate={new Date()}
                                    minDate={new Date("2019-01-01")}
                                    onChange={(newValue) => {
                                        const d = newValue.toISOString().substring(0, newValue.toISOString().indexOf("T"));
                                        setDate(d);
                                    }}
                                    inputFormat="dd/MM/yyyy"
                                    renderInput={(params) => <TextField {...params} />}
                                />
                            </LocalizationProvider>
                        </Box>
                        {getHistory()}
                    </Paper>
                </Grid>
                : ``}
            </Grid>
        </>
    );
}

export default Home;