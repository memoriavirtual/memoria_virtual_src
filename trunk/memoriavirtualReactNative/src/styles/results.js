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
    justifyContent: 'center',
    alignItems: 'center',
    width: wp('93.3%'),
    marginBottom: hp('1%'),
  },
  resultsRowSpacer: {
    width: wp('3%'),
  },
  customButtonStyle: {
    width: wp('45%'),
    height: hp('5.78%'),
    backgroundColor: '#B4B4B4',
    borderRadius: 10,
    borderWidth: 0.2,
    justifyContent: 'center',
    alignItems: 'center',
    elevation: 5,
  },
  customButtonText: {
    fontSize: 12,
    color: 'black',
    fontWeight: 'bold',
  },
});

export default resultsStyles;
