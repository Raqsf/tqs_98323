import { useState, useEffect }from 'react';
import { Grid, Typography, Paper, Box, LinearProgress, Button } from '@mui/material';
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
    const [date, setDate] = useState('');

    const handleCountry = (value) => {
        //console.log(value);
        if(value !== "Global") {
            setCountry(value);
        } else {
            setCountry('');
        }

        value !== "Global" ? setIsSelected(true) : setIsSelected(false);
        setIsLoaded(false);
      };

    const showMoreStats = () => {
        setLimit(limit + 5);
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
        return someDate.getDate() == today.getDate() &&
          someDate.getMonth() == today.getMonth() &&
          someDate.getFullYear() == today.getFullYear()
    }

    const toDate = (someDateString) => {
        const time = new Date(someDateString);
        return time.toLocaleString();
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

    /* console.log("XXXX " + country)
    console.log(items) */

    useEffect(() => {
        console.log("HERE")
        if(country.length > 0){
            const url = date == "" ? `/history/${country}` : `/history/${country}&day=${date}`
            axios.get(url)
            .then(res => {
                setIsLoadedHist(true);
                setItemsHist(res.data);
                console.log(res.data)
                //console.log(isToday(res.data[0].time))
            })
            .catch(err => {
                setIsLoadedHist(true);
                setErrorHist(err);
            })
        }
    }, [country]);

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
    
    } else {
        //console.log(items.length)
        //console.log(limit > items.length)
        //console.log(itemsHist)

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
                        style={{ minWidth: '75vw', padding: 8, backgroundColor: "#F8F2E9" }}
                    >
                        <Typography variant="h6" component="span">
                            TODAY'S INFO {isSelected ? `IN ${country.toUpperCase()}` : ``}
                        </Typography>
                        {!isLoaded ? <LinearProgress /> :
                            items.slice(0,(limit > items.length ? items.length : limit)).map((item,i) => (<Box> <Stats key={i} {...item}/></Box>))
                        }
                        {isSelected ? "" : <Box><Button onClick={showMoreStats}>Show More</Button> <Button onClick={showLessStats}>Show Less</Button></Box>}
                    </Paper>
                </Grid>
                <Grid item xs={1} textAlign='center'>
                    <Paper 
                        elevation={2} 
                        style={{ minWidth: '75vw', padding: 8, backgroundColor: "#F8F2E9" }}
                    >
                        <Typography variant="h6" component="span">
                            {isSelected ? `${country.toUpperCase()}` : ``} HISTORY
                        </Typography>
                        {!isLoaded ? <LinearProgress /> :
                            itemsHist.slice(0,(limit > itemsHist.length ? itemsHist.length : limit)).map((item,i) => 
                            isToday(item.time) ?
                                (<Box> <Typography>{toDate(item.time)}</Typography><Stats key={i} {...item}/></Box>)
                            : "")
                        }
                    </Paper>
                </Grid>
            </Grid>
        </>
    );
    }
}

export default Home;