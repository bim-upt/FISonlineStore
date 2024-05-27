import './App.css';
import api from './api/axiosConfig';
import { useState, useEffect } from 'react';
import Layout from './components/Layout'; 
import {Routes, Route} from 'react-router-dom';
import Home from './components/home';

function App() {
  const [products, setProducts] = useState();

  const getProducts = async () => {
  
    try {
      const response = await axios.get('/api/products'); // Aici trebuie să fie adresa corectă a endpoint-ului care returnează produsele
      
      setProducts(response.data);

    } catch (error) {
      console.error('Error fetching products:', error);
    }

  } 

  useEffect(() => {
    getProducts();
  }, [])

  return (
    <div className = "App">
      <Routes> 
        <Route path="/" element={<Layout />} > </Route>
        <Route path="/" element={<Home />} > </Route>
      </Routes>

    </div>
  );
}

export default App;
