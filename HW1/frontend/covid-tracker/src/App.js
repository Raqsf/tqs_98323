import './App.css';
// import NavBar from './components/NavBar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ResponsiveAppBar from './components/ResponsiveAppBar';

function App() {
  return (
    <>
      <Router>
        <ResponsiveAppBar />
        {/* <NavBar /> */}
        <Routes>
          <Route path="/" exact />
        </Routes>
      </Router>
    </>
  );
}

export default App;
