import {
  // heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

const textInfoStyles = () => ({
  inputs: {
    flexDirection: 'row',
    alignItems: 'center',
    width: wp('90%'),
    marginTop: wp('3%'),
    justifyContent: 'space-between',
  },
  spacer: {
    height: wp('9%'),
    borderLeftWidth: 1,
    borderLeftColor: '#262626',
  },
  textInput: {
    height: wp('10%'),
  },
  formTitle: {
    height: wp('10%'),
    width: wp('60%'),
  },
  formInput: {
    flexDirection: 'row',
    height: wp('10%'),
    width: wp('25%'),
  },
  infoText: {
    fontSize: 18,
  },
});

export default textInfoStyles;
