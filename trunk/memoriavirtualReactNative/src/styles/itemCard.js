import {
  heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

const itemCardStyles = () => ({
  // Common styles
  mainContainer: {
    // flex: 1,
    width: wp('90%'),
    justifyContent: 'flex-start',
    alignItems: 'center',
    paddingHorizontal: wp('5%'),
    paddingBottom: hp('2%'),
    marginVertical: hp('2%'),
    backgroundColor: '#F2F2F2',
    borderRadius: 20,
    borderWidth: 0.1,
    elevation: 10,
  },
  titleContainer: {
    justifyContent: 'center',
    alignItems: 'center',
    marginVertical: hp('2%'),

  },
  titleText: {
    textAlign: 'center',
    fontSize: 20,
    fontWeight: 'bold',
  },
  valueContainer: {
    marginBottom: hp('2%'),
    paddingHorizontal: wp('3%'),
    paddingVertical: hp('3%'),
    borderRadius: 20,
    borderWidth: 0.2,
    borderColor: '#000000',
    backgroundColor: '#FFFFFF'
  },

  // Large text stypes
  valueLargeText: {
    textAlign: 'justify',
  },
});

export default itemCardStyles;