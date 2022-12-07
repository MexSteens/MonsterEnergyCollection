import './App.css';
import Footer from './Components/Footer';
import { Outlet } from 'react-router-dom';

function App() {
  return (
    <div className="app flex flex-col h-screen">
      <body className="app-body flex-grow">
        <Outlet />
      </body>
      {/* <footer>
        <Footer />
      </footer> */}
    </div>
  );
}

export default App;
