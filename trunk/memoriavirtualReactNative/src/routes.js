import { createStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';

import HomeScreen from './screens/HomeScreen';
import ResultsScreen from './screens/ResultsScreen';
import Search from './screens/Search';
import GeneralInfo from './screens/GeneralInfo';
import Disponibility from './screens/Disponibility';
import Production from './screens/Production';
import SubjectsDescriptors from './screens/SubjectsDescriptors';
import State from './screens/State';


const MainNavigator = createStackNavigator({
  Home: { screen : HomeScreen },
  Results: { screen: ResultsScreen },
  Search: { screen : Search },
  GeneralInfo: { screen : GeneralInfo },
  Disponibility: { screen : Disponibility },
  Production: { screen : Production },
  SubjectsDescriptors: { screen : SubjectsDescriptors },
  State: { screen : State },
}, {
  initialRouteName: 'Results',
  defaultNavigationOptions: {
    headerTitleStyle: {
      color: 'white',
      fontWeight: 'bold',
      fontSize: 22,
    },
    headerStyle: {
      backgroundColor: '#4488BB',
    },
  },
});

const appContainer = createAppContainer(MainNavigator);

export default appContainer;