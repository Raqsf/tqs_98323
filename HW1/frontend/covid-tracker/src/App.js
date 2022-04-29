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
      coral: '#E27D60',
      lightBlue: '#85DCBA',
      orange: '#E8A87C',
      pink: '#C38D9E'
    },
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
