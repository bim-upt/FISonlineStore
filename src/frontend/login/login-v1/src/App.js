import './App.css';
import api from './components/axiosConfig';
import { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Home from './components/Home';
import Header from './components/Header'; // Importă componenta Header

function App() {
  const [products, setProducts] = useState([]);

  const getProducts = async () => {
    try {
      const response = await api.get('/api/products');
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <div className="App">
      <Header /> {/* Include componenta Header */}
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home products={products} />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
