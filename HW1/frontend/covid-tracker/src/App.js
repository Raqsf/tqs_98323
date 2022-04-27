import './App.css';
// import NavBar from './components/NavBar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ResponsiveAppBar from './components/ResponsiveAppBar';
import Home from './pages/Home';
import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  palette: {
    primary: {
      main: '#41A3B3',
      contrastText: '#fff',
      lighBlue: '#85DCBA',
      orange: '#E8A87C',
      pink: '#C38D9E'
    },
    secondary: { 
      // coral
      main: '#E27D60',
      contrastText: '#fff',
    }
  },
});

function App() {
  return (
    <>
      <Router>
        <ResponsiveAppBar />
        <Routes>
          <Route path="/" exact element={<Home />}/>
        </Routes>
      </Router>
    </>
  );
}

export default App;
