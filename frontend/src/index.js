import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import CreateProduct from './Components/createProduct';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProductList from './Components/productList';
import ProductDetails from './Components/productDetails';

ReactDOM.render(
  <Router>
    <Routes>
      <Route path="/" element={<ProductList />} />
      <Route path="/create" element={<CreateProduct />} />
      <Route path="/products/:id" element={<ProductDetails />} />
    </Routes>
  </Router>,
  document.getElementById('root')
);
