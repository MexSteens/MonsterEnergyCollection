import Footer from '../Components/Footer';
import "./Log.css"
import LogCard from '../Components/LogCard';
import ScannedEnergyCanService from '../Services/ScannedEnergyCanService'
import { useState } from 'react';
import { useEffect } from 'react';

const Log = () => {
  const initialEnergyCan = {
    id: null,
    name: "",
    description: "",
    imagePath: "",
    category: ""
  }

  const intialScannedEnergyCan = {
    id: null,
    userEntity: {
      id: localStorage.getItem('userId')
    },
    energyCanEntity: initialEnergyCan,
    created: ""
  }

  const [scannedEnergyCan, setScannedEnergyCan] = useState(intialScannedEnergyCan)

  const getScannedEnergyCanByUserId = canprops => {
    ScannedEnergyCanService.findByUser(canprops)
      .then(response => {
        setScannedEnergyCan(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  const canprops = {
    userId: localStorage.getItem('userId')
  }

  useEffect(() => {
    getScannedEnergyCanByUserId(canprops)
  }, [])

    return <div className="container flex flex-col h-screen">
    <div className="padding p-4 flex-grow">
      <h1 className="text-3xl font-bold underline text-center">Recently scanned cans</h1>
      { scannedEnergyCan[0] && scannedEnergyCan.map((scannedCan, i) => {return (<LogCard scannedEnergyCan={scannedCan} />)})}
    </div>
    <footer>
        <Footer />
      </footer>
    </div>;
  };
  
  export default Log;