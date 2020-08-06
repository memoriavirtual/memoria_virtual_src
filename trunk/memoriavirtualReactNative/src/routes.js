import { createStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';

import HomeScreen from './screens/HomeScreen';
import ResultsScreen from './screens/ResultsScreen';
import SearchScreen from './screens/SearchScreen';
import GeneralInfoScreen from './screens/GeneralInfoScreen';
import DisponibilityScreen from './screens/DisponibilityScreen';
import ProductionScreen from './screens/ProductionScreen';
import SubjectsDescriptorsScreen from './screens/SubjectsDescriptorsScreen';
import StateScreen from './screens/StateScreen';
import AuthorshipScreen from './screens/AuthorshipScreen';
import DescriptionScreen from './screens/DescriptionScreen';
import SourceScreen from './screens/SourceScreen';

const MainNavigator = createStackNavigator({
  Home: { screen: HomeScreen },
  Results: { screen: ResultsScreen },
  Search: { screen: SearchScreen },
  GeneralInfo: { screen: GeneralInfoScreen },
  Disponibility: { screen: DisponibilityScreen },
  Production: { screen: ProductionScreen },
  SubjectsDescriptors: { screen: SubjectsDescriptorsScreen },
  State: { screen: StateScreen },
  Authorship: { screen: AuthorshipScreen },
  Description: { screen: DescriptionScreen },
  Source: { screen: SourceScreen },
}, {
  initialRouteName: 'Home',
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
