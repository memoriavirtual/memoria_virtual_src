import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

const resultsStyles = () => ({
  resultsContainer: {
    flex: 1,
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
  },

  // Row
  resultsRowContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    width: wp('93.3%'),
  },
  customButtonStyle: {
    width: wp('45%'),
    height: hp('5.78%'),
    backgroundColor: '#B4B4B4',
    borderRadius: 10,
    justifyContent: 'center',
    alignItems: 'center',
  },
  customButtonText: {
    fontSize: 12,
    color: 'black',
    fontWeight: 'bold',
  },
});

export default resultsStyles;
