import { createStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';

import HomeScreen from './screens/HomeScreen';
import Search from './screens/Search';
import GeneralInfo from './screens/GeneralInfo';
import Disponibility from './screens/Disponibility';
import Production from './screens/Production';
import SubjectsDescriptors from './screens/SubjectsDescriptors';
import State from './screens/State';


const MainNavigator = createStackNavigator({
  Home: { screen : HomeScreen },
  Search: { screen : Search },
  GeneralInfo: { screen : GeneralInfo },
  Disponibility: { screen : Disponibility },
  Production: { screen : Production },
  SubjectsDescriptors: { screen : SubjectsDescriptors },
  State: { screen : State },
}, {
  initialRouteName: 'State',
});

const appContainer = createAppContainer(MainNavigator);

export default appContainer;